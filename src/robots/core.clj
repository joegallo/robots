(ns robots.core
  (:use [robots.engine :only [engine run-battle]]))

(def eng (engine "/home/joe/robocode/" true))

(do 
  ;; (compile 'robots.fatrobot)
  (run-battle eng 3 [800 600] 
	      ["sample.SittingDuck" 
	       "sample.Fire" 
	       "sample.Crazy"
	       "sample.RamFire" 
	       ;;"robots.fatrobot*"
	       ]))
