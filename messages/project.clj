(defproject com.github.zjjfly/messages "0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  ;:plugins []
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-encoding" "utf8" "-Xlint:-options"]
  :resource-paths ["src/main/resources"]
  :test-paths ["src/test/clj" "src/test/java"]
  :target-path "target/%s"
  :aot :all
  :profiles {:uberjar {:aot :all}})
