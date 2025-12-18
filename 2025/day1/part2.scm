#!r7rs

(import (scheme base) (scheme write) (scheme file))

(define (print x)
  (write x)
  (newline)
  x)

(define (read-file-as-lines filename)
  (let ((port (open-input-file filename)))
    (letrec ((read-loop (lambda (accum)
               (let ((line (read-line port)))
                 (if (eof-object? line)
                     (begin
                       (close-input-port port)
                       (reverse accum))
                     (read-loop (cons line accum)))))))
      (read-loop '()))))

(define (get-dir line)
  (substring line 0 1))

(define (get-nb line)
  (string->number
   (substring line
              1
              (string-length line))))
(define input (read-file-as-lines "input.txt"))

(define start 50)
(define max 100)

(define (increase-code? cur-nb line max)
  (not (= 0 (count-clic line cur-nb max))))

(define (compute line cur-nb max)
  (cond
    ((equal? (get-dir line) "R")
     (modulo (+ (get-nb line) cur-nb) max))
    (else
     (modulo (- cur-nb (get-nb line)) max))))

(define (count-clic line cur-nb max)
  (cond
    ((equal? (get-dir line) "R")
     (quotient (+ (get-nb line) cur-nb) max))
    ((> (- cur-nb (get-nb line)) 0)
     0)
    ((= cur-nb 0)
     (quotient (get-nb line) max))
    (else
     (+ 1 (quotient (abs (- cur-nb (get-nb line))) max)))))

;(print "--- test ---")
;(print (count-clic "R1000" 50 max)) ; => 10
;(print (count-clic "R2" 98 max)) ; => 1
;(print (compute "R2" 98 max)) ; => 0
;(print (count-clic "L1000" 50 max)) ; => 10

;(print "--- right ---")
;(print (count-clic "R10" 50 max))  ; => 0
;(print (count-clic "R50" 50 max))  ; => 1 
;(print (count-clic "R51" 50 max))  ; => 1 
;(print (count-clic "R200" 0 max))  ; => 2 


;(print "--- left ---")
;(print (count-clic "L10" 50 max))  ; => 0 
;(print (count-clic "L50" 50 max))  ; => 1 
;(print (count-clic "L10" 5 max))   ; => 1
;(print (count-clic "L205" 0 max))  ; => 2

;(print "--- tricky cases ---")
;(print (count-clic "R5" 98 max))   ; => 1
;(print (count-clic "L1" 0 max))    ; => 0
;(print (count-clic "L100" 100 max)); => 1
;(print (count-clic "L10" 0 max))   ; => 0


(define (main file start max)
  (let loop ((cur-file file)
             (code 0)
             (cur-nb start))
    (cond
      ((null? cur-file)
       code)
      ((increase-code? cur-nb (car cur-file) max)
       (loop (cdr cur-file)
             (+ (count-clic (car cur-file) cur-nb max) code)
             (compute (car cur-file) cur-nb max)))
      (else
       (loop (cdr cur-file)
             code
             (compute (car cur-file) cur-nb max))))))

(print "Code :")
(print (main input start max))





      

