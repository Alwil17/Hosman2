import { DatePipe, registerLocaleData } from '@angular/common';
import { AbstractControl, AsyncValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
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

  export function markAllControlsAsTouched(fGroup : FormGroup): void {
    Object.keys(fGroup.controls).forEach((controlName) => {
      const control = fGroup.get(controlName);
      control!.markAsTouched();
    });
  }

  export function slugify(str: String) {
    return String(str)
      .normalize('NFKD') // split accented characters into their base characters and diacritical marks
      .replace(/[\u0300-\u036f]/g, '') // remove all the accents, which happen to be all in the \u03xx UNICODE block.
      .trim() // trim leading or trailing whitespace
      .toLowerCase() // convert to lowercase
      .replace(/[^a-z0-9 -]/g, '') // remove non-alphanumeric characters
      .replace(/\s+/g, '-') // replace spaces with hyphens
      .replace(/-+/g, '-'); // remove consecutive hyphens
  }
  
  export function hasStateChanges(state: any, olddata: any, newdata: any){
    return state !== null && state !== undefined && state.length === 0 ||
    JSON.stringify(olddata) !== JSON.stringify(newdata)
  }