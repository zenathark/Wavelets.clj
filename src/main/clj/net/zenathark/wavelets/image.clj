(ns net.zenathark.wavelets.image
  (:gen-class)
  (:require [mikera.image.core :as mimage])
  (:import javax.imageio.ImageIO
           java.io.ByteArrayOutputStream
           java.io.ByteArrayInputStream))

(def ^:const arch-size 8)
(def ^:const arch-bit-size (* arch-size 8))

;; Represents an RGB color
(defrecord RGB [red green blue])

;; Represents a Gray color
(defrecord Gray [gray])

(defn get-channel-mask
  "Creates a binary mask for color channel extraction and normalization.
   Creates a mask used for color extraction of packed color representations
   where size is the size in bytes of the color."
  [size]
  (unsigned-bit-shift-right -1 (- arch-bit-size (* size 8))))

(defn make-rgb
  ([packed-color size]
   "Creates a normalied RGB record.
   Creates a normalized color from a packed rgb representation where
   size is the amount of bytes used per channel."
   (make-rgb packed-color size false))
  ([packed-color size bgr?]
   "Creates a normalied RGB record.
   Creates a normalized color from a packed rgb representation where
   size is the amount of bytes used per channel. If bgr? is set to true,
   it is assumed that the color comes in bgr format."
   (let [channel-mask (get-channel-mask size)
         c3 (bit-and packed-color channel-mask)
         c2 (bit-and (unsigned-bit-shift-right packed-color (* size 8)) channel-mask)
         c1 (bit-and (unsigned-bit-shift-right packed-color (* size 8 2)) channel-mask)]
     (if bgr?
       (make-rgb c3 c2 c1 size)
       (make-rgb c1 c2 c3 size))))
  ([r g b size]
   "Creates a normalied RGB record. The colors are normalized according to its
    original size in bytes."
   (let [max-value (get-channel-mask size)
         nr (float (/ r max-value))
         ng (float (/ g max-value))
         nb (float (/ b max-value))]
     (->RGB nr ng nb))))

(defn image->output-stream
  "Creates an output stream from an in-memory image."
  [image fmt]
  (let [output-stream (ByteArrayOutputStream.)]
    (ImageIO/write image (name fmt) output-stream)
    output-stream))

(defn image->input-stream
  "Creates an input stream from an in-memory image"
  [image fmt]
  (-> image
      (image->output-stream fmt)
      (.toByteArray)
      (ByteArrayInputStream.)))

(defn image->vector
  "Creates a vector representation of an in-memory image"
  [image]
  (let [w (mimage/width image)
        h (mimage/height image)
        raster (mimage/get-pixels image)]
    (vector (for [i (range h)]
              (vector (for [j (range w)]
                        (-> raster
                            (nth (+ (* i h) j))
                            (make-rgb 1))))))))
