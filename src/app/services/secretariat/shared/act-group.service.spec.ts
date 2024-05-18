import { TestBed } from '@angular/core/testing';

import { ActGroupService } from './act-group.service';

describe('ActGroupService', () => {
  let service: ActGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActGroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
