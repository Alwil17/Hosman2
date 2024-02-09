import { TestBed } from '@angular/core/testing';

import { TherapeuticClassService } from './therapeutic-class.service';

describe('TherapeuticClassService', () => {
  let service: TherapeuticClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TherapeuticClassService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
