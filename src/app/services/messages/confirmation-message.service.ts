import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon } from 'sweetalert2';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

 async openConfirmationDialog(message?: string, icon?: SweetAlertIcon){
  let res = false
  const result = await Swal.fire({
      title: 'Confirmation',
      html: message || 'Êtes-vous sûr de vouloir continuer ?',
      icon: icon || 'info',
      showCancelButton: true,
      confirmButtonText: 'Continuer',
      cancelButtonText: 'Non',
      reverseButtons: true,
    })
    if (result.value) {
      return true;
    }

    return res;
  }
}
