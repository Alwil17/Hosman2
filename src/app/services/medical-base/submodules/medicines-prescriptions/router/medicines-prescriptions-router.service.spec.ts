import { TestBed } from '@angular/core/testing';

import { MedicinesPrescriptionsRouterService } from './medicines-prescriptions-router.service';

describe('MedicinesPrescriptionsRouterService', () => {
  let service: MedicinesPrescriptionsRouterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicinesPrescriptionsRouterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
