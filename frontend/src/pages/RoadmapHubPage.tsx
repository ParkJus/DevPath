import { useEffect, useState } from 'react'
import { roadmapApi } from '../lib/api'
import { readStoredAuthSession } from '../lib/auth-session'
import type { RoadmapHubCatalog, RoadmapHubItem } from '../types/roadmap-hub'

function buildRoadmapHref(linkedRoadmapId: number | null) {
  return linkedRoadmapId ? `roadmap.html?original=${linkedRoadmapId}` : null
}

function renderRoleCard(item: RoadmapHubItem) {
  const href = buildRoadmapHref(item.linkedRoadmapId)
  const iconClass = item.iconClass?.trim() || 'fas fa-map'
  const cardClassName = item.featured
    ? 'roadmap-hub-card relative overflow-hidden rounded-lg border-2 border-brand bg-green-50/30 p-5 shadow-md'
    : 'roadmap-hub-card rounded-lg border border-gray-200 p-5 shadow-sm'

  const content = (
    <>
      <div className={item.featured ? 'relative mb-2 flex justify-between' : 'mb-2 flex justify-between'}>
        <h3 className={item.featured ? 'font-bold text-brand' : 'font-bold text-gray-900'}>{item.title}</h3>
        <i className={`${iconClass} ${item.featured ? 'text-brand' : 'text-gray-400'}`} />
      </div>
      <p className={item.featured ? 'relative text-xs text-gray-500' : 'text-xs text-gray-500'}>
        {item.subtitle || '공식 로드맵'}
        {item.featured ? (
          <span className="ml-1 font-bold text-brand">
            (추천)
          </span>
        ) : null}
      </p>
      {item.featured ? <div className="absolute top-2 right-2 h-2 w-2 animate-ping rounded-full bg-red-500" /> : null}
    </>
  )

  if (!href) {
    return (
      <div key={`${item.title}-${item.sortOrder}`} className={`${cardClassName} cursor-default opacity-70`}>
        {content}
      </div>
    )
  }

  return (
    <a key={`${item.title}-${item.sortOrder}`} href={href} className={cardClassName}>
      {content}
    </a>
  )
}

function renderSkillChip(item: RoadmapHubItem) {
  const href = buildRoadmapHref(item.linkedRoadmapId)
  const chipKey = `${item.title}-${item.sortOrder}`
  const className = 'skill-btn rounded border border-gray-200 px-4 py-2 text-left text-sm text-gray-700 shadow-sm'

  if (!href) {
    return (
      <button key={chipKey} type="button" className={`${className} cursor-default`}>
        {item.title}
      </button>
    )
  }

  return (
    <a key={chipKey} href={href} className={className}>
      {item.title}
    </a>
  )
}

function renderLinkListItem(item: RoadmapHubItem) {
  const href = buildRoadmapHref(item.linkedRoadmapId)
  const itemKey = `${item.title}-${item.sortOrder}`

  if (!href) {
    return (
      <li key={itemKey}>
        <div className="flex justify-between rounded border border-gray-200 bg-white p-3 shadow-sm">
          <span>{item.title}</span>
          <i className="fas fa-chevron-right mt-1.5 text-xs text-gray-300" />
        </div>
      </li>
    )
  }

  return (
    <li key={itemKey}>
      <a
        href={href}
        className="flex justify-between rounded border border-gray-200 bg-white p-3 shadow-sm transition hover:bg-gray-50"
      >
        <span>{item.title}</span>
        <i className="fas fa-chevron-right mt-1.5 text-xs text-gray-400" />
      </a>
    </li>
  )
}

function SectionHeading({
  accentClassName,
  title,
}: {
  accentClassName: string
  title: string
}) {
  return (
    <div className="mb-8 flex items-center gap-4">
      <span className={`h-8 w-1 rounded-full ${accentClassName}`} />
      <h2 className="text-2xl font-bold text-gray-900">{title}</h2>
    </div>
  )
}

function LinkSection({
  iconClassName,
  iconToneClassName,
  title,
  items,
}: {
  iconClassName: string
  iconToneClassName: string
  title: string
  items: RoadmapHubItem[]
}) {
  return (
    <section>
      <h2 className="mb-4 flex items-center gap-2 border-b border-gray-200 pb-2 text-xl font-bold text-gray-900">
        <i className={`${iconClassName} ${iconToneClassName}`} /> {title}
      </h2>
      <ul className="space-y-2">{items.map(renderLinkListItem)}</ul>
    </section>
  )
}

function RoadmapHubSections({
  catalog,
  loading,
  error,
  onRetry,
}: {
  catalog: RoadmapHubCatalog | null
  loading: boolean
  error: string | null
  onRetry: () => void
}) {
  if (loading) {
    return (
      <div className="rounded-3xl border border-gray-200 bg-white px-6 py-16 text-center text-sm text-gray-500 shadow-sm">
        <i className="fas fa-circle-notch mr-2 animate-spin" />
        로드맵 허브 구성을 불러오는 중입니다.
      </div>
    )
  }

  if (error) {
    return (
      <div className="rounded-3xl border border-rose-200 bg-rose-50 px-6 py-12 text-center shadow-sm">
        <p className="text-sm font-semibold text-rose-600">{error}</p>
        <button
          type="button"
          onClick={onRetry}
          className="mt-4 rounded-full border border-rose-200 bg-white px-5 py-2 text-sm font-bold text-rose-600 transition hover:bg-rose-50"
        >
          다시 불러오기
        </button>
      </div>
    )
  }

  const sections = catalog?.sections ?? []
  const cardSections = sections.filter((section) => section.layoutType === 'CARD_GRID')
  const chipSections = sections.filter((section) => section.layoutType === 'CHIP_GRID')
  const linkSections = sections.filter((section) => section.layoutType === 'LINK_LIST')

  return (
    <>
      {cardSections.map((section) => (
        <section key={section.sectionKey}>
          <SectionHeading accentClassName="bg-brand" title={section.title} />
          <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {section.items.map(renderRoleCard)}
          </div>
        </section>
      ))}

      {chipSections.map((section) => (
        <section key={section.sectionKey}>
          <SectionHeading accentClassName="bg-yellow-400" title={section.title} />
          <div className="grid grid-cols-2 gap-3 md:grid-cols-4 lg:grid-cols-6">
            {section.items.map(renderSkillChip)}
          </div>
        </section>
      ))}

      {linkSections.length > 0 ? (
        <div className={`grid grid-cols-1 gap-12 ${linkSections.length > 1 ? 'md:grid-cols-2' : ''}`}>
          {linkSections.map((section, index) => (
            <LinkSection
              key={section.sectionKey}
              iconClassName={index === 0 ? 'fas fa-lightbulb' : 'fas fa-check-circle'}
              iconToneClassName={index === 0 ? 'text-yellow-400' : 'text-brand'}
              title={section.title}
              items={section.items}
            />
          ))}
        </div>
      ) : null}
    </>
  )
}

function RoadmapHubPage() {
  const session = readStoredAuthSession()
  const [catalog, setCatalog] = useState<RoadmapHubCatalog | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const abortController = new AbortController()

    // 로드맵 허브 화면이 열릴 때 최신 허브 구성을 바로 가져온다.
    const loadHubCatalog = async () => {
      setLoading(true)
      setError(null)

      try {
        const response = await roadmapApi.getHubCatalog(abortController.signal)
        setCatalog(response)
      } catch (loadError) {
        if (abortController.signal.aborted) {
          return
        }

        setError(loadError instanceof Error ? loadError.message : '로드맵 허브를 불러오지 못했습니다.')
      } finally {
        if (!abortController.signal.aborted) {
          setLoading(false)
        }
      }
    }

    void loadHubCatalog()

    return () => {
      abortController.abort()
    }
  }, [])

  const displayName = session?.name?.trim() || '게스트'
  const avatarSeed = encodeURIComponent(displayName)

  return (
    <div className="h-screen min-w-0 overflow-hidden bg-gray-50 text-gray-900">
      <div className="hub-header-rail" />

      <div className="flex h-screen min-w-0 flex-1 flex-col overflow-hidden">
        <header className="hub-header">
          <div className="app-responsive-container flex h-full items-center gap-4 lg:gap-8">
            <div
              className="hidden w-60 items-center px-4 lg:flex"
              style={{ transform: 'translateX(var(--logo-nudge))' }}
            >
              <a href="home.html" className="group flex items-center gap-2 text-xl font-bold text-gray-900">
                <i className="fas fa-code-branch text-brand transition group-hover:rotate-12" /> DevPath
              </a>
            </div>
            <div className="flex items-center lg:hidden">
              <a href="home.html" className="group flex items-center gap-2 text-xl font-bold text-gray-900">
                <i className="fas fa-code-branch text-brand transition group-hover:rotate-12" /> DevPath
              </a>
            </div>

            <div className="hidden flex-1 items-center justify-center gap-10 text-sm font-bold text-gray-500 md:flex">
              <a href="roadmap-hub.html" className="border-b-2 border-brand pb-1 text-brand transition">
                로드맵
              </a>
              <a href="lecture-list.html" className="transition hover:text-brand">강의</a>
              <a href="project-list.html" className="transition hover:text-brand">프로젝트</a>
              <a href="community-list.html" className="transition hover:text-brand">커뮤니티</a>
              <a href="job-matching.html" className="transition hover:text-brand">채용분석</a>
            </div>

            <div className="ml-auto flex min-w-0 items-center justify-end gap-2 md:w-60">
              <div
                className="flex cursor-pointer items-center gap-2"
                onClick={() => { window.location.href = 'profile.html' }}
              >
                <span className="text-sm font-bold text-gray-700">{displayName}</span>
                <img
                  src={`https://api.dicebear.com/7.x/avataaars/svg?seed=${avatarSeed}`}
                  className="h-9 w-9 rounded-full border border-gray-200 shadow-sm"
                  alt="me"
                />
              </div>
            </div>
          </div>
        </header>

        <main className="hub-main flex-1">
          <header className="border-b border-gray-100 bg-gradient-to-b from-white to-gray-50 px-4 py-20 text-center">
            <h1 className="mb-6 text-4xl font-bold text-gray-900 md:text-6xl">
              <span className="bg-gradient-to-r from-purple-600 to-green-500 bg-clip-text text-transparent">
                개발자 로드맵
              </span>
            </h1>
            <p className="mx-auto mb-10 max-w-3xl text-lg leading-relaxed text-gray-500">
              <span className="font-bold text-brand">DevPath</span>는 개발자들의 학습 방향을 잃지 않도록 정리합니다.
              <br />
              역할과 기술별로 정리된 로드맵을 확인하고 성장 흐름을 시작하세요.
            </p>
            <div className="flex flex-col justify-center gap-4 sm:flex-row">
              <button
                type="button"
                onClick={() => { window.location.href = 'my-roadmap.html' }}
                className="group relative flex items-center justify-center gap-3 rounded-full bg-brand px-8 py-4 font-bold text-white shadow-lg transition-all duration-300 hover:-translate-y-1 hover:bg-green-600 hover:shadow-xl"
              >
                <div className="h-2 w-2 animate-pulse rounded-full bg-white" />
                <span className="text-lg">나만의 로드맵 만들기</span>
                <i className="fas fa-pen-ruler ml-1 transition-transform group-hover:rotate-12" />
              </button>
              <button
                type="button"
                onClick={() => { window.location.href = 'roadmap.html' }}
                className="group relative flex items-center justify-center gap-3 rounded-full bg-gray-800 px-8 py-4 font-bold text-white shadow-lg transition-all duration-300 hover:-translate-y-1 hover:bg-gray-900 hover:shadow-xl"
              >
                <span className="text-lg">학습 로드맵으로 이동</span>
                <i className="fas fa-arrow-right transition-transform group-hover:translate-x-1" />
              </button>
            </div>
          </header>

          <div className="mx-auto mt-12 max-w-7xl space-y-20 px-6 pb-32">
            <RoadmapHubSections
              catalog={catalog}
              loading={loading}
              error={error}
              onRetry={() => {
                setLoading(true)
                setError(null)

                void roadmapApi.getHubCatalog()
                  .then((response) => {
                    setCatalog(response)
                  })
                  .catch((retryError) => {
                    setError(retryError instanceof Error ? retryError.message : '로드맵 허브를 불러오지 못했습니다.')
                  })
                  .finally(() => {
                    setLoading(false)
                  })
              }}
            />
          </div>
        </main>
      </div>
    </div>
  )
}

export default RoadmapHubPage
