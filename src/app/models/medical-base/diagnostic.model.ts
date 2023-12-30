export interface IDiagnostic {
  id: string;
  title: string;
  stemId: string;
  hasCodingNote: boolean;
  chapter: string;
  theCode: string;
  description?: string;
}

export class Diagnostic {
  id: string;
  title: string;
  stemId: string;
  hasCodingNote: boolean;
  chapter: string;
  theCode: string;
  description?: string;

  constructor(iDiagnostic: IDiagnostic) {
    this.id = iDiagnostic.id;
    this.title = iDiagnostic.title;
    this.stemId = iDiagnostic.stemId;
    this.hasCodingNote = iDiagnostic.hasCodingNote;
    this.chapter = iDiagnostic.chapter;
    this.theCode = iDiagnostic.theCode;
    this.description = iDiagnostic.description;
  }
}
