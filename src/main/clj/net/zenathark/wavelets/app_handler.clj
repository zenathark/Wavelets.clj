(ns net.zenathark.wavelets.app-handler
  (:gen-class)
  (:require [compojure.handler :as handler]
            [compojure.route :as croute]
            [net.zenathark.wavelets.image :as zimage]
            [ring.util.response :refer [response content-type]]
            [compojure.core :as ccore]))

;; (defroutes app
;;   (GET "/test" [] (hello-answer)))

(defn response-image [image fmt]
  (-> image
      (zimage/image->input-stream fmt)
      (response)
      (content-type "image/png"))) 

(defprotocol ServerRoutes
  (add-route! [this route])
  (get-routes [this]))

(defprotocol ImageDisplay
  (imshow! [this image fmt image-name]))

(deftype ServerState [routez])

(extend-type ServerState
  ServerRoutes
  (add-route! [this route]
    (swap! (.routez this) conj route)
    nil)
  (get-routes [this]
    (apply ccore/routes @(.routez this))))

(extend-type ServerState
  ImageDisplay
  (imshow! [this image fmt image-name]
    (add-route! this (ccore/GET (str "/" image-name) [] (response-image image fmt)))))

(defn make-server-state []
  (->ServerState (atom [])))

(def server-state (make-server-state))

(defn app [x]
  ((get-routes server-state) x))

;; (def app
;;   (-> app-routes
;;       wrap-log-request))
