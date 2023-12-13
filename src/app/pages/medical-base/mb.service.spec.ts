import { TestBed } from '@angular/core/testing';

import { MbService } from './mb.service';

describe('MbService', () => {
  let service: MbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
