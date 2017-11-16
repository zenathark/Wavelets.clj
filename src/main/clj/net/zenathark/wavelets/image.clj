(ns net.zenathark.wavelets.image
  (:gen-class)
  (:require [mikera.image.core :as mimage])
  (:import javax.imageio.ImageIO
           java.io.ByteArrayOutputStream
           java.io.ByteArrayInputStream))

(defn image->output-stream
  [image fmt]
  (let [output-stream (ByteArrayOutputStream.)]
    (ImageIO/write image (name fmt) output-stream)
    output-stream))

(defn image->input-stream
  [image fmt]
  (-> image
      (image->output-stream fmt)
      (.toByteArray)
      (ByteArrayInputStream.)))
