(ns robots.engine
  (:use clojure.contrib.shell-out)
  (:import (java.io File)
           (robocode.control RobocodeEngine BattlefieldSpecification BattleSpecification)
           (robocode.control.events BattleAdaptor)))

(System/setProperty "NOSECURITY" "true")

(defn battle-console []
  (proxy [BattleAdaptor] []
    (onBattleMessage [e] (println "Msg> " (.getMessage e)))
    (onBattleError [e] (println "Err> " (.getError e)))
    (onBattleCompleted
     [e]
     (println "\n\n-- Battle Complete --\n")
     (doseq [result (.getSortedResults e)]
       (println (.getTeamLeaderName result) (.getScore result))))))

(defn engine [dir vis?]
  (let [engine (doto (RobocodeEngine. (File. dir))
                 (.setVisible vis?)
                 (.addBattleListener (battle-console)))]
    (sh "jar" "xf" "lib/clojure-1.1.0.jar" "clojure")
    (sh "mv" "clojure" "classes/")
    engine))

(defn battle [engine rounds size robots]
  (let [[x y] size
        field (BattlefieldSpecification. x y)]
    (BattleSpecification.
     rounds field
     (into-array
      (reduce (fn [h v]
                (conj h (first (.getLocalRepository engine v))))
              [] robots)))))

(defn run-battle
  ([engine rounds size robots & wait]
     (run-battle engine (battle engine rounds size robots))
     (if (not (nil? wait))
       (.waitTillBattleOver engine)))
  ([engine battle]
     (.runBattle engine battle false)))

(defn close [engine]
  (.close engine)
  (sh "rm" "-rf" "classes/")
  (sh "mkdir" "classes"))
