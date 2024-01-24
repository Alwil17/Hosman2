import { DatePipe, registerLocaleData } from '@angular/common';
import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { Observable, catchError, from, map, of } from 'rxjs';
import * as Yup from 'yup';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr);

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


  export function formatDate(date: Date, format: string): string {
    const pipe = new DatePipe("fr-FR")
    return pipe.transform(date, format) ?? "";
  }
  