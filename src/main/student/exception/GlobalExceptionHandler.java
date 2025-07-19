@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){
    List<String> errs = ex.getBindingResult().getFieldErrors()
      .stream().map(f -> f.getField()+": "+f.getDefaultMessage()).toList();
    return ResponseEntity.badRequest().body(errs);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAll(Exception ex){ return ResponseEntity.status(500).body(ex.getMessage()); }
}
