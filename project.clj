(def kitchensink-version "3.5.5")
(def trapperkeeper-version "4.3.2")
(def i18n-version "1.0.3")

(defproject org.openvoxproject/trapperkeeper-authorization "2.1.5-SNAPSHOT"
  :description "Trapperkeeper authorization system"
  :url "http://github.com/openvoxproject/trapperkeeper-authorization"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :min-lein-version "2.9.1"

  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort

  ;; Generally, try to keep version pins in :managed-dependencies and the libraries
  ;; this project actually uses in :dependencies, inheriting the version from
  ;; :managed-dependencies. This prevents endless version conflicts due to deps of deps.
  ;; Renovate should keep the versions largely in sync between projects.
  :managed-dependencies [[org.clojure/clojure "1.12.4"]
                         [org.clojure/tools.logging "1.3.1"]
                         [org.clojure/tools.namespace "1.5.1"]
                         [cheshire "5.13.0"]
                         [commons-codec "1.20.0"]
                         [commons-io "2.21.0"]
                         [org.bouncycastle/bcpkix-jdk18on "1.83"]
                         [org.bouncycastle/bcprov-jdk18on "1.83"]
                         [org.openvoxproject/i18n ~i18n-version]
                         [org.openvoxproject/http-client "2.2.3"]
                         [org.openvoxproject/kitchensink "3.5.5"]
                         [org.openvoxproject/kitchensink "3.5.5" :classifier "test"]
                         [org.openvoxproject/rbac-client "1.2.3"]
                         [org.openvoxproject/ring-middleware "2.1.3"]
                         [org.openvoxproject/ssl-utils "3.6.2"]
                         [org.openvoxproject/trapperkeeper "4.3.2"]
                         [org.openvoxproject/trapperkeeper "4.3.2" :classifier "test"]
                         [org.openvoxproject/trapperkeeper-webserver-jetty10 "1.1.2"]
                         [prismatic/schema "1.4.1"]
                         [ring/ring-codec "1.3.0"]
                         [ring/ring-core "1.15.3"]
                         [ring/ring-mock "0.6.2"]
                         [slingshot "0.12.2"]]

  :dependencies [[org.clojure/clojure]
                 [org.clojure/tools.logging]
                 [org.openvoxproject/i18n]
                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/rbac-client]
                 [org.openvoxproject/ring-middleware]
                 [org.openvoxproject/ssl-utils]
                 [org.openvoxproject/trapperkeeper]
                 [prismatic/schema]
                 [ring/ring-codec]
                 [slingshot]]

  ;; By declaring a classifier here and a corresponding profile below we'll get an additional jar
  ;; during `lein jar` that has all the code in the test/ directory. Downstream projects can then
  ;; depend on this test jar using a :classifier in their :dependencies to reuse the test utility
  ;; code that we have.
  :classifiers [["test" :testutils]]

  :profiles {:dev {:aliases {"ring-example"
                             ["trampoline" "run"
                              "-b" "./examples/ring_app/bootstrap.cfg"
                              "-c" "./examples/ring_app/ring-example.conf"]}
                   :source-paths ["examples/ring_app/src"]
                   :dependencies [[org.openvoxproject/trapperkeeper-webserver-jetty10]
                                  [org.openvoxproject/trapperkeeper :classifier "test" :scope "test"]
                                  [org.openvoxproject/kitchensink :classifier "test" :scope "test"]
                                  [org.clojure/tools.namespace]
                                  [org.bouncycastle/bcprov-jdk18on]
                                  [org.bouncycastle/bcpkix-jdk18on]
                                  [ring/ring-mock]]}
             :testutils {:source-paths ^:replace ["test"]}}

  ;; this plugin is used by jenkins jobs to interrogate the project version
  :plugins [[jonase/eastwood "1.4.3" :exclusions [org.clojure/clojure]]
            [org.openvoxproject/i18n ~i18n-version]]

  :lein-release        {:scm          :git
                        :deploy-via   :lein-deploy}

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]]

  :main puppetlabs.trapperkeeper.main)
