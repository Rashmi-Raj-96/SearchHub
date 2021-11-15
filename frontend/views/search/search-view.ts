import { LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';


@customElement('search-view')
export class ImageCard extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
}