(defproject net.zenathark/cloveletz "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "https://tldrlegal.com/license/mit-license"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/imagez "0.12.0"]]
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :resource-paths ["src/main/resources"]
  :test-paths ["src/test/clj"]
  :profiles {:dev {:resource-paths ["src/test/resources"]
                   :dependencies [[clj-stacktrace "0.2.8"]]}
             :uberjar {:aut :all}}
  :main net.zenathark.wavelets.core
  :target-path "target/%s")
