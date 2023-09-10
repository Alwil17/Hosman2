export interface ICollectionRequest {
  provenance: string;
  date_encaissement: Date;
  mode_payements: {}[];
}

export class CollectionRequest {
  provenance: string;
  date_encaissement: Date;
  mode_payements: {}[];

  constructor(iCollection: ICollectionRequest) {
    this.provenance = iCollection.provenance;
    this.date_encaissement = iCollection.date_encaissement;
    this.mode_payements = iCollection.mode_payements;
  }
}
