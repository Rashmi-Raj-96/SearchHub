import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';
import '@vaadin/vaadin-button';


@customElement('image-card')
export class ImageCard extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<li class="bg-contrast-5 flex flex-col items-start p-m rounded-l" style="background-color: #f0f0f0; height: 80vh !important">
      <div
        class="bg-contrast flex items-center justify-center mb-m overflow-hidden rounded-m w-full"
        style="height: 350px;"
      >
        <img id="image" class="w-full" style="height: 60vh !important" />
      </div>
      <span class="text-xl font-semibold" id="header"></span>
      </br>
      <span class="text-l font-semibold" id="subtitle"></span>
      <p class="my-m" id="text"></p>
      <a id="anchor"><span class="text-l font-bold" theme="badge" id="badge"></span></a>
    </li> `;
  }
}
