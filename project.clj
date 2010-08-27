(defproject robots "1.0.0-SNAPSHOT"
  :description "Clojure Robots"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 ;;  [robocode "1.7.0.2"]
		 [org.nakkaya.robocode/robocode "1.7.2.0"]
		 ]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :manifest {"robots" "robots.fatrobot"}
  :aot [robots.fatrobot])
