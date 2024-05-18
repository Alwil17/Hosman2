import { TestBed } from '@angular/core/testing';

import { MedicalBaseRouterService } from './medical-base-router.service';

describe('MedicalBaseRouterService', () => {
  let service: MedicalBaseRouterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicalBaseRouterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
