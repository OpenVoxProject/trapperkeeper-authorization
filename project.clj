(def kitchensink-version "3.5.5")
(def trapperkeeper-version "4.3.2")
(def i18n-version "1.0.3")

(defproject org.openvoxproject/trapperkeeper-authorization "2.1.2-SNAPSHOT"
  :description "Trapperkeeper authorization system"
  :url "http://github.com/openvoxproject/trapperkeeper-authorization"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :min-lein-version "2.9.1"

  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort

  ;; These are to enforce consistent versions across dependencies of dependencies,
  ;; and to avoid having to define versions in multiple places. If a component
  ;; defined under :dependencies ends up causing an error due to :pedantic? :abort,
  ;; because it is a dep of a dep with a different version, move it here.
  :managed-dependencies [[org.clojure/clojure "1.12.4"]

                         [ring/ring-core "1.15.3"]
                         [ring/ring-codec "1.3.0"]
                         [commons-codec "1.20.0"]
                         [cheshire "5.13.0"]
  
                         [org.openvoxproject/kitchensink ~kitchensink-version]
                         [org.openvoxproject/kitchensink ~kitchensink-version :classifier "test"]
                         [org.openvoxproject/trapperkeeper ~trapperkeeper-version]
                         [org.openvoxproject/trapperkeeper ~trapperkeeper-version :classifier "test"]]

  :dependencies [[org.clojure/clojure]

                 [org.clojure/tools.logging "1.3.1"]
                 [slingshot "0.12.2"]
                 [prismatic/schema "1.4.1"]
                 [ring/ring-codec]

                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/trapperkeeper]
                 [org.openvoxproject/rbac-client "1.2.1"]
                 [org.openvoxproject/ring-middleware "2.1.0"]
                 [org.openvoxproject/ssl-utils "3.6.2"]
                 [org.openvoxproject/i18n ~i18n-version]]

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
                   :dependencies [[org.openvoxproject/trapperkeeper-webserver-jetty10 "1.1.2"]
                                  [org.openvoxproject/trapperkeeper :classifier "test" :scope "test"]
                                  [org.openvoxproject/kitchensink :classifier "test" :scope "test"]
                                  [org.clojure/tools.namespace "1.5.1"]
                                  [org.bouncycastle/bcprov-jdk18on "1.83"]
                                  [org.bouncycastle/bcpkix-jdk18on "1.83"]
                                  [ring/ring-mock "0.6.2"]]}
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
