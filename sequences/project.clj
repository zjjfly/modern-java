(defproject com.github.zjjfly/sequences "0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.typesafe.akka/akka-actor_2.12 "2.5.19"]
                 [com.github.zjjfly/messages "0.1"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [liu.mars/jaskell "0.2.9"]
                 [org.postgresql/postgresql "42.2.4"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.clojure/core.specs.alpha "0.2.44" :exclusions [[org.clojure/clojure] [org.clojure/spec.alpha]]]
                 [org.clojure/spec.alpha "0.2.176" :exclusions [[org.clojure/clojure]]]
                 [com.taoensso/nippy "2.14.0"]
                 [cheshire "5.9.0"]
                 ;[com.github.romix.akka/akka-kryo-serialization_2.12 "0.5.2"]
                 [junit/junit "4.12"]
                 [com.typesafe.akka/akka-testkit_2.12 "2.5.19"]]
  :plugins [[lein-junit "1.1.8"]]
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java" "src/test/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-encoding" "utf8" "-Xlint:-options"]
  :resource-paths ["src/main/resources"]
  :test-paths ["src/test/clj" "src/test/java"]
  :target-path "target/%s"
  :aot :all
  :junit ["src/test/java"]
  :profiles {:server {:main jjzi.sequences.App
                      :jvm-opts ["-Dconfig.resource=junit.conf"]}
             ;:client {:main liu.mars.Client
             ;         :jvm-opts ["-Dconfig.resource=client.conf"]}
             })
