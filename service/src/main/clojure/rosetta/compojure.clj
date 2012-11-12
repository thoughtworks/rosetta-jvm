(ns rosetta.compojure
  (:gen-class
    :name rosetta.Compojure
    :methods [[invoke [] java.lang.Long]]))

(defn -invoke [this]
  (+ 2 2))
