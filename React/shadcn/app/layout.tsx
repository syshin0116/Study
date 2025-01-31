"use client";

import "@/app/globals.css";
import * as React from "react";
import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { AppSidebar } from "@/components/app-sidebar";
import { Navbar } from "@/components/navbar";

// 최상위 레이아웃
export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
     
      <body className="flex flex-col min-h-screen">
        <SidebarProvider>
          <Navbar />
          <div className="flex flex-1 overflow-hidden">
          <AppSidebar />
          
          
      <main className="flex-1 overflow-auto p-6">
        {children}
            </main>
            </div>
    </SidebarProvider>
      </body>
    </html>
  );
}
