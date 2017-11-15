(defproject net.zenathark/cloveletz "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "https://tldrlegal.com/license/mit-license"}
  :repositories [["java.net" "https://download.java.net/maven/2"]
                 ["sonatype" {:url "https://oss.sonatype.org/content/repositories/releases"
                              ;; If a repository contains releases only setting
                              ;; :snapshots to false will speed up dependencies.
                              :snapshots false
                              ;; Disable signing releases deployed to this repo.
                              ;; (Not recommended.)
                              :sign-releases false
                              ;; You can also set the policies for how to handle
                              ;; :checksum failures to :fail, :warn, or :ignore.
                              :checksum :fail
                              ;; How often should this repository be checked for
                              ;; snapshot updates? (:daily, :always, or :never)
                              :update :always
                              ;; You can also apply them to releases only:
                              :releases {:checksum :fail :update :always}}]
                 ["nuxeo" "https://maven-eu.nuxeo.org/nexus/content/repositories/public-releases/"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.mikera/imagez "0.12.0"]
                 [compojure "1.5.1"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]]
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :resource-paths ["src/main/resources"]
  :test-paths ["src/test/clj"]
  :profiles {:dev {:resource-paths ["src/test/resources"]
                   :dependencies [[clj-stacktrace "0.2.8"]
                                  [javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}
             :uberjar {:aut :all}}
  :main net.zenathark.wavelets.core
  :plugins [[lein-ring "0.12.1"]]
  :ring {:handler net.zenathark.wavelets.app-handler/app
         :nrepl {:start? true
                 :port 8080}}
  :target-path "target/%s")
