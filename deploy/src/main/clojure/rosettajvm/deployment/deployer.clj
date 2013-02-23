(ns rosettajvm.deployment.deployer
  (:use rosettajvm.deployment.bootstrap
        clojure.tools.cli)
  (:gen-class
    :name "rosettajvm.deployment.Deployer"))

(defn parse [args]
  (cli args
    ["-p" "--port" "The port on which the deployment servlet should listen." :default 8080 :parse-fn #(Integer. %)]
    ["-h" "--help" "Display usage guide."]))

(defn -main [& args]
  (let [parse-results (parse args)
        options       (parse-results 0)
        arguments     (parse-results 1)
        usage         (parse-results 2)]
    (if (contains? options :help)
      (println usage)
      (bootstrap options))))