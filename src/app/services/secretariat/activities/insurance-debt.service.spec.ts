import { TestBed } from '@angular/core/testing';

import { InsuranceDebtService } from './insurance-debt.service';

describe('InsuranceDebtService', () => {
  let service: InsuranceDebtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InsuranceDebtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
