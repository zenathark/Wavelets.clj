(ns net.zenathark.wavelets.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty];[clojure.core.matrix :as m]
            [mikera.image.core :as mik])
  (:import [java.awt.image BufferedImage])
  (:use compojure.core))

(defn -main
  [& args]
  (println "Hello, Clojure!"))

(defn -runner
  [handler]
  (jetty/run-jetty handler {:port 8080}))

(defn apphandler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello from ring"})

(defn tens []
  (println "Hello, Clojure!"))

(defrecord RGB [red green blue])
(defrecord Gray [gray])

(defn convert
  [tp color]
  (case tp
    :Gray (Gray. (+ (* 0.299 (:red color))
                    (* 0.587 (:green color))
                    (* 0.114 (:blue color))))))

(defn image->color-matrix
  [^BufferedImage image]
  (let [pixels (mik/get-pixels image)]
    (mapv (fn [i]
            (map (fn [j]
                   (let [pixel (bit-and 0xFFFFFFFF (nth pixels i j))
                         red   (bit-shift-right (bit-and 0x00FF0000 pixel) 16)
                         green (bit-shift-right (bit-and 0x0000FF00 pixel) 8)
                         blue  (bit-and 0x000000FF pixel)]
                     (RGB. red green blue)))
                 (range (mik/width image))))
          (range (mik/height image) ))))

(defn convert-image
  [tp image]
  (mapv #(mapv (fn [pixel] (convert tp pixel)) %) image))
