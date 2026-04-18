import './my-roadmap.css'
import MyRoadmapBuilderApp from './MyRoadmapBuilderApp'
import { renderPage } from './render-page'

renderPage(<MyRoadmapBuilderApp />, { missingRootMessage: 'my-roadmap root element was not found' })