import { TestBed } from '@angular/core/testing';

import { InsuranceTypeService } from './insurance-type.service';

describe('InsuranceTypeService', () => {
  let service: InsuranceTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InsuranceTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
