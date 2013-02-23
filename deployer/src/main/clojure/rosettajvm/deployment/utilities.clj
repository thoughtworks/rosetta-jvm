(ns rosettajvm.deployment.utilities
  (:use [clojure.pprint :only [pprint]])
  (:import java.io.StringWriter))

(defn hashmap-to-string [map]
  (let [w (StringWriter.)]
    (pprint map w)
    (.toString w)))
