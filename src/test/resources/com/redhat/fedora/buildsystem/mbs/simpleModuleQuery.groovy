node() {
    def mbsRequest = null
    waitUntil {
        mbsRequest = queryModuleBuildRequest mbsUrl: "http://localhost:32000", moduleRequestId: "1"
        return mbsRequest.isModuleReady()
    }
    echo "Module id: " + mbsRequest.getId() + " is ready"
    echo "${mbsRequest}"
}