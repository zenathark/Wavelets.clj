(ns net.zenathark.wavelets.image
  (:gen-class)
  (:require [mikera.image.core :as mimage])
  (:import javax.imageio.ImageIO
           java.io.ByteArrayOutputStream))

(defn image->output-stream
  [image fmt]
  (let [output-stream (ByteArrayOutputStream.)]
    (ImageIO/write image (name fmt) output-stream)
    output-stream))
