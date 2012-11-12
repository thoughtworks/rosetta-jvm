(ns rosetta.resources.health
  (:gen-class
    :name rosetta.resources.HealthResource
    :methods [[show [] java.lang.String]]))

(defn -show [this]
  "{\"status\": \"up\"}")
