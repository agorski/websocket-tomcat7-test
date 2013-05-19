package de.albgorski.test.websocket;

public class DaneDto {
  private String nazwa;
  private String opis;

  public DaneDto(String nazwa, String opis) {
    this.nazwa = nazwa;
    this.opis = opis;
  }

  public String getNazwa() {
    return nazwa;
  }

  public void setNazwa(String nazwa) {
    this.nazwa = nazwa;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DaneDto{");
    sb.append("nazwa='").append(nazwa).append('\'');
    sb.append(", opis='").append(opis).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
