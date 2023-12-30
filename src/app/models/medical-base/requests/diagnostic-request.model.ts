export interface IDiagnosticRequest {
  diagnostic: string;
  commentaire?: string;
}

export class DiagnosticRequest {
  diagnostic: string;
  commentaire?: string;

  constructor(iDiagnosticRequest: IDiagnosticRequest) {
    this.diagnostic = iDiagnosticRequest.diagnostic;
    this.commentaire = iDiagnosticRequest.commentaire;
  }
}
