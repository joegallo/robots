(ns robots.core
  (:import (java.io File)
           (robocode.control BattleSpecification BattlefieldSpecification
                             RobocodeEngine)
           (robocode.control.events BattleAdaptor)))

;; nosecurity allows clojure robots to work, robotpath tells robocode
;; where our clojure robots are located
(System/setProperty "NOSECURITY" "true")
(System/setProperty "ROBOTPATH" "target/classes")

(defn battle-console []
  (proxy [BattleAdaptor] []
    (onBattleMessage [e] (println "Msg> " (.getMessage e)))
    (onBattleError [e] (println "Err> " (.getError e)))
    (onBattleCompleted
      [e]
      (println "\n\n-- Battle Complete --\n")
      (doseq [result (.getSortedResults e)]
        (println (.getTeamLeaderName result) (.getScore result))))))

(defn make-engine []
  (doto (RobocodeEngine.)
    (.addBattleListener (battle-console))))

(defn make-robots [engine robots]
  (for [robot robots]
    (first (.getLocalRepository engine (str robot)))))

(defn make-battle [rounds [x y] robots]
  (BattleSpecification. rounds
                        (BattlefieldSpecification. x y)
                        (into-array robots)))

(defn run-battle
  ([engine battle]
     (.setVisible engine true)
     (.runBattle engine battle true)))

(defn compile* [& libs]
  (doseq [lib libs]
    (compile (symbol lib))))

(defn fight [& robots]
  (apply compile* robots)
  (with-open [engine (make-engine)]
    (run-battle engine
                (make-battle 3
                             [800 600]
                             (make-robots engine robots)))))
