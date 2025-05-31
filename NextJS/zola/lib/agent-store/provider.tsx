"use client"

import { Agent } from "@/app/types/agent"
import {
  fetchAgentBySlugOrId,
  fetchCuratedAgentsFromDb,
  fetchUserAgentsFromDb,
} from "@/lib/agent-store/api"
import { useChatSession } from "@/lib/chat-store/session/provider"
import { usePathname, useSearchParams } from "next/navigation"
import {
  createContext,
  Suspense,
  useCallback,
  useContext,
  useEffect,
  useState,
} from "react"
import { useChats } from "../chat-store/chats/provider"

// Create a separate component that uses useSearchParams
function SearchParamsProvider({
  setAgentSlug,
}: {
  setAgentSlug: (slug: string | null) => void
}) {
  const searchParams = useSearchParams()

  useEffect(() => {
    const agentSlug = searchParams.get("agent")
    setAgentSlug(agentSlug)
  }, [searchParams, setAgentSlug])

  return null
}

type AgentContextType = {
  currentAgent: Agent | null
  curatedAgents: Agent[] | null
  userAgents: Agent[] | null
}

const AgentContext = createContext<AgentContextType | undefined>(undefined)

type AgentProviderProps = {
  children: React.ReactNode
  userId?: string | null
}

export const AgentProvider = ({ children, userId }: AgentProviderProps) => {
  const pathname = usePathname()
  const [agentSlug, setAgentSlug] = useState<string | null>(null)
  const { getChatById } = useChats()
  const { chatId } = useChatSession()
  const currentChat = chatId ? getChatById(chatId) : null
  const [currentAgent, setCurrentAgent] = useState<Agent | null>(null)
  const currentChatAgentId = currentChat?.agent_id || null
  const [curatedAgents, setCuratedAgents] = useState<Agent[] | null>(null)
  const [userAgents, setUserAgents] = useState<Agent[] | null>(null)

  const fetchCuratedAgents = useCallback(async () => {
    const agents = await fetchCuratedAgentsFromDb()
    if (agents) setCuratedAgents(agents)
  }, [])

  const fetchUserAgents = useCallback(async () => {
    if (!userId) return
    const agents = await fetchUserAgentsFromDb(userId)
    if (agents) setUserAgents(agents)
  }, [userId])

  const fetchCurrentAgent = useCallback(async () => {
    if (!agentSlug && !currentChatAgentId) {
      setCurrentAgent(null)
      return
    }

    const agent = await fetchAgentBySlugOrId({
      slug: agentSlug || undefined,
      id: currentChatAgentId || undefined,
    })

    setCurrentAgent(agent)
  }, [agentSlug, currentChatAgentId])

  useEffect(() => {
    if (!agentSlug && !currentChatAgentId) {
      setCurrentAgent(null)
      return
    }

    fetchCurrentAgent()
  }, [pathname, agentSlug, currentChatAgentId, fetchCurrentAgent])

  useEffect(() => {
    fetchCuratedAgents()
  }, [fetchCuratedAgents])

  useEffect(() => {
    if (!userId) {
      return
    }

    fetchUserAgents()
  }, [fetchUserAgents, userId])

  return (
    <>
      <Suspense fallback={null}>
        <SearchParamsProvider setAgentSlug={setAgentSlug} />
      </Suspense>

      <AgentContext.Provider
        value={{ currentAgent, curatedAgents, userAgents }}
      >
        {children}
      </AgentContext.Provider>
    </>
  )
}

export const useAgent = () => {
  const context = useContext(AgentContext)
  if (!context)
    throw new Error("useAgentContext must be used within AgentProvider")
  return context
}
