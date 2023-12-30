export interface IMotif {
  id: number;
  libelle: string;
  //   slug: string;
}

export class Motif {
  id: number;
  libelle: string;
  //   slug: string;

  constructor(iMotif: IMotif) {
    this.id = iMotif.id;
    this.libelle = iMotif.libelle;
    // this.slug = iMotif.slug;
  }
}

// static fromResponse(patient: PatientResponse): Patient {
//     return new Patient({
//       id: patient.id,
//       reference: patient.reference,
//       nom: patient.nom,
//       prenoms: patient.prenoms,
//       date_naissance: patient.date_naissance,
//       sexe: patient.sexe,
//       tel1: patient.tel1,
//       is_assure: patient.is_assure,
//       date_ajout: patient.date_ajout,
//       personne_a_prevenir: PersonToContact.fromResponse(
//         patient.personne_a_prevenir
//       ),
//       adresse: Address.fromResponse(patient.adresse),
//       pays_origine: Country.fromResponse(patient.pays_origine),
//       assurance: patient.assurance
//         ? Insurance.fromResponse(patient.assurance)
//         : undefined,
//       taux_assurance: patient.taux_assurance,
//       date_debut_assurance: patient.date_debut_assurance,
//       date_fin_assurance: patient.date_fin_assurance,
//       // patient_assurance: patient.patient_assurance
//       //   ? PatientInsurance.fromResponse(patient.patient_assurance)
//       //   : undefined,
//       lieu_naissance: patient.lieu_naissance,
//       tel2: patient.tel2,
//       type_piece: patient.type_piece,
//       no_piece: patient.no_piece,
//       profession: patient.profession
//         ? Profession.fromResponse(patient.profession)
//         : undefined,
//       employeur: patient.employeur
//         ? Employer.fromResponse(patient.employeur)
//         : undefined,

//       // Visit/Medical base fileds
//       maladies: patient.maladies ? patient.maladies : undefined,
//       parents: patient.parents ? patient.parents : undefined,
//       commentaire: patient.commentaire,
//       antecedent: patient.antecedent,
//     });
//   }
