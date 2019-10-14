package energy.viridis.exercise.error;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import energy.viridis.exercise.exception.BadRequestException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> notFoundError(Exception ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> updateConstraintError(ConstraintViolationException ex) {

		log.info(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Unfilled field(s): " + ex.getConstraintViolations().stream().map(constraint -> constraint.getPropertyPath().toString()).collect(Collectors.joining(", ")) + ".");
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> badResquestError(BadRequestException ex) {

		log.info(ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("Erro: " + ex.getMessage());
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> emptyDataResultError(Exception ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

	}
}
