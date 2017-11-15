(ns net.zenathark.wavelets.app-handler
  (:gen-class)
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [net.zenathark.wavelets.image :as zimage]
            [ring.util.response :refer [response content-type]]))

(defn- hello-answer []
  "Hello")

(defroutes app
  (GET "/test" [] (hello-answer)))

(defroutes app-routes
  (route/not-found
   (response "Not Found")))

(defn wrap-log-request [handler]
  (fn [req]
    (println req)
    (handler req)))

(defn response-image [image fmt]
  (-> image
      zimage/image->output-stream fmt
      response
      content-type "image/png"))

(def app
  (-> app-routes
      wrap-log-request))
