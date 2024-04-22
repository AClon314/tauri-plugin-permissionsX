use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PingRequest {
    pub value: Option<String>,
}

#[derive(Debug, Clone, Default, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct PingResponse {
    pub value: Option<String>,
}

// added
#[derive(Debug, Default, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PersistNotifyRequest {
    pub title: Option<String>,
    pub content: Option<String>,
}
