(defproject org.openvoxproject/trapperkeeper-authorization "2.0.2-SNAPSHOT"
  :description "Trapperkeeper authorization system"
  :url "http://github.com/openvoxproject/trapperkeeper-authorization"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :min-lein-version "2.7.1"

  :parent-project {:coords [org.openvoxproject/clj-parent "7.4.1-SNAPSHOT"]
                   :inherit [:managed-dependencies]}

  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort

  :dependencies [[org.clojure/clojure]

                 [org.clojure/tools.logging]
                 [slingshot]
                 [prismatic/schema]
                 [ring/ring-codec]

                 [org.openvoxproject/kitchensink]
                 [org.openvoxproject/trapperkeeper]
                 [org.openvoxproject/rbac-client]
                 [org.openvoxproject/ring-middleware]
                 [org.openvoxproject/ssl-utils]
                 [org.openvoxproject/i18n]]

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
                   :dependencies [[org.openvoxproject/trapperkeeper-webserver-jetty9]
                                  [org.openvoxproject/trapperkeeper nil :classifier "test" :scope "test"]
                                  [org.openvoxproject/kitchensink nil :classifier "test" :scope "test"]
                                  [org.clojure/tools.namespace "1.4.1"]
                                  [org.bouncycastle/bcprov-jdk18on]
                                  [org.bouncycastle/bcpkix-jdk18on]
                                  [ring/ring-mock]]}
             :testutils {:source-paths ^:replace ["test"]}}

  ;; this plugin is used by jenkins jobs to interrogate the project version
  :plugins [[lein-parent "0.3.9"]
            [jonase/eastwood "1.2.2" :exclusions [org.clojure/clojure]]
            [org.openvoxproject/i18n "0.9.3-SNAPSHOT"]]

  :lein-release        {:scm          :git
                        :deploy-via   :lein-deploy}

  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]]

  :main puppetlabs.trapperkeeper.main)
