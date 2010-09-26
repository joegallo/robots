(ns robots.core
  (:import [java.io File]
           [robocode.control RobocodeEngine BattlefieldSpecification BattleSpecification]
           [robocode.control.events BattleAdaptor]))

;; nosecurity allows clojure robots to work, robotpath tells robocode
;; where our clojure robots are located
(System/setProperty "NOSECURITY" "true")
(System/setProperty "ROBOTPATH" "classes")

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

(def *engine* (make-engine))

(defn make-robots [robots]
  (for [robot robots]
    (first (.getLocalRepository *engine* (str robot)))))

(defn make-battle [rounds [x y] robots]
  (BattleSpecification.
   rounds
   (BattlefieldSpecification. x y)
   (into-array robots)))

(defn run-battle
  ([rounds size robots]
     (.setVisible *engine* true)
     (.runBattle *engine* (make-battle rounds size robots) true)))

(defn compile* [& libs]
  (doseq [lib libs]
    (compile (symbol lib))))

(defn fight [& robots]
  (apply compile* robots)
  (run-battle 3 [800 600] (make-robots robots)))
