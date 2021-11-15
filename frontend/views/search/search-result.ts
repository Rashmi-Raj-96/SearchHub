import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout';
import '@vaadin/vaadin-select';
import '@vaadin/vaadin-button';
// @ts-ignore
import { applyTheme } from '../../generated/theme';



@customElement('super-search-view')
export class SuperSearchView extends LitElement {
  connectedCallback() {
    super.connectedCallback();
    // Apply the theme manually because of https://github.com/vaadin/flow/issues/11160
    applyTheme(this.renderRoot);
  }

  render() {
    return html`
     <main style="background-color: #d5e1df; ">
       <vaadin-horizontal-layout class="items-center justify-between">
         <vaadin-vertical-layout>
         <h2></h2>
         <vaadin-button style="font-weight: bold; background-color: #ffffff; border: 2px solid #000000; margin-left : 4px ; color:#1676f3" onclick="location.href='http://localhost:9090/search';">      <iron-icon icon="vaadin:angle-left"></iron-icon>Search Again</vaadin-button>
         <h2 class="mb-0 mt-xl text-3xl" id="header" style="margin-left: 6px;"></h2>
         <p class="mb-xl mt-0 text-secondary"></p>
         </vaadin-vertical-layout>
         <vaadin-select id="sortBy" label="Sort By"></vaadin-select>
       </vaadin-horizontal-layout>
       <ol class="gap-m grid list-none m-0 p-0">
         <slot></slot>
       </ol>
     </main>
    `;
  }
}