import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { Observable, catchError, from, map, of } from 'rxjs';
import * as Yup from 'yup';

export function validateYupSchema<T>(
    yupSchema: Yup.Schema<T>
  ): AsyncValidatorFn {
    return (
      control: AbstractControl
    ): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
      return from(yupSchema.validate(control.value)).pipe(
        map((isValid) => (isValid ? null : {})),
        catchError((e) => of({ [e.path ? e.path : 'yup']: e.message }))
      );
    };
  } 