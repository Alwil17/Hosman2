import { City } from "./city.model";
import { Neighborhood } from "./neighborhood.model";
import { AddressResponse } from "./responses/address-response.model";

export interface IAddress {
  id: number;
  ville: City;
  quartier: Neighborhood;
  rue?: string;
  bp?: string;
  arrondissement?: string;
  no_maison?: string;
}

export class Address {
  id: number;
  ville: City;
  quartier: Neighborhood;
  rue?: string;
  bp?: string;
  arrondissement?: string;
  no_maison?: string;

  constructor(iAddress: IAddress) {
    this.id = iAddress.id;
    this.ville = iAddress.ville;
    this.quartier = iAddress.quartier;
    this.rue = iAddress.rue;
    this.bp = iAddress.bp;
    this.arrondissement = iAddress.arrondissement;
    this.no_maison = iAddress.no_maison;
  }

  static fromResponse(address: AddressResponse) {
    return new Address({
      id: address.id,
      ville: City.fromResponse(address.ville),
      quartier: Neighborhood.fromResponse(address.quartier),
      rue: address.rue,
      bp: address.bp,
      arrondissement: address.arrondissement,
      no_maison: address.no_maison,
    });
  }

  toText() {
    return (
      this.ville.nom +
      ", " +
      this.quartier.nom +
      ", " +
      (this.rue ?? "--- ") +
      ", " +
      (this.bp ?? "--- ") +
      ", " +
      (this.arrondissement ?? "--- ") +
      ", " +
      (this.no_maison ? "Maison n° " + this.no_maison : "--- ")
    );
  }

  // constructor(
  //   id: number,
  //   ville: City,
  //   quartier: Neighborhood,
  //   rue?: string,
  //   bp?: string,
  //   arrondissement?: string,
  //   no_maison?: string
  // ) {
  //   this.id = id;
  //   this.ville = ville;
  //   this.quartier = quartier;
  //   this.rue = rue;
  //   this.bp = bp;
  //   this.arrondissement = arrondissement;
  //   this.no_maison = no_maison;
  // }

  // static emptyAddress(): Address {
  //   return new Address(-1, new City(-1, ""), new Neighborhood(-1, ""));
  // }
}
