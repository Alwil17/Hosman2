import { TestBed } from '@angular/core/testing';

import { AtrdService } from './atrd.service';

describe('AtrdService', () => {
  let service: AtrdService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtrdService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
