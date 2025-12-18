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
;(define input (read-file-as-lines "input-easy.txt"))

(define start 50)
(define max 100)

(define (increase-code? cur-nb)
  (= 0 cur-nb))

(define (compute line cur-nb max)
  (cond
    ((equal? (get-dir line) "R")
     (modulo (+ (get-nb line) cur-nb) max))
    (else
     (modulo (- cur-nb (get-nb line)) max))))

;(print (compute "R23" 20 max))
;(print (compute "R85" 20 max)) 
;(print (compute "L23" 26 max)) 
;(print (compute "L23" 20 max)) 
          

(define (main file start max)
  (let loop ((cur-file file)
             (code 0)
             (cur-nb start))
    (cond
      ((null? cur-file)
       code)
      ((increase-code? (compute (car cur-file) cur-nb max))
       (loop (cdr cur-file)
             (+ 1 code)
             (compute (car cur-file) cur-nb max)))
      (else
       (loop (cdr cur-file)
             code
             (compute (car cur-file) cur-nb max))))))

(print "Code :")
(print (main input start max))





      

