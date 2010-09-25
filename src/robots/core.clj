(ns robots.core
  (:import [java.io File]
           [robocode.control RobocodeEngine BattlefieldSpecification BattleSpecification]
           [robocode.control.events BattleAdaptor]))

;; nosecurity allows clojure robots to work, robotpath tells robocode
;; where our clojure robots are located
(System/setProperty "NOSECURITY" "true")
(System/setProperty "ROBOTPATH" "classes")

;; TODO add your robot here
(compile 'robots.fatrobot)
;; (compile 'your.robot.here)

(defn battle-console []
  (proxy [BattleAdaptor] []
    (onBattleMessage [e] (println "Msg> " (.getMessage e)))
    (onBattleError [e] (println "Err> " (.getError e)))
    (onBattleCompleted
     [e]
     (println "\n\n-- Battle Complete --\n")
     (doseq [result (.getSortedResults e)]
       (println (.getTeamLeaderName result) (.getScore result))))))

(defn engine []
  (doto (RobocodeEngine.)
    (.setVisible true)
    (.addBattleListener (battle-console))))

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

;; this is the main entry point
;; call this with robots as string names
(defn fight [& robots]
  (run-battle (engine)
              3 ;; # rounds
              [800 600] ;; battle size
              robots))
