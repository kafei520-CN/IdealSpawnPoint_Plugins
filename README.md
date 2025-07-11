# IdealSpawnPoint插件 | 理想的复活点

## 功能特性

- **多世界支持**：为每个世界独立管理复活点
- **精确控制**：通过坐标设置精确的复活位置
- **权限系统**：细粒度的权限控制
- **多语言支持**：内置中英文语言包
- **自动重载**：定时自动重载配置
- **可视化列表**：查看所有世界的复活点信息

## 安装指南

1. 将插件jar文件放入服务器的`plugins`文件夹
2. 重启服务器
3. 插件会自动生成配置文件

## 命令列表

| 命令 | 描述 | 权限 |
|------|------|------|
| `/isp add <ID>` | 添加复活点 | `idealspawn.add` |
| `/isp remove <ID>` | 移除复活点 | `idealspawn.remove` |
| `/isp list` | 列出当前世界复活点 | `idealspawn.list` |
| `/isp listall` | 列出所有世界复活点 | `idealspawn.list.all` |
| `/isp removeall` | 移除当前世界所有复活点 | `idealspawn.removeall` |
| `/isp reload` | 重载配置 | `idealspawn.reload` |

## 权限节点

- `idealspawn.use` - 使用主命令的基本权限
- `idealspawn.add` - 添加复活点权限
- `idealspawn.remove` - 移除复活点权限
- `idealspawn.list` - 查看复活点列表权限
- `idealspawn.list.all` - 查看所有世界复活点权限
- `idealspawn.removeall` - 移除所有复活点权限
- `idealspawn.reload` - 重载配置权限

## 配置说明

配置文件位于`plugins/IdealSpawnPoint/config.yml`，主要配置项：

```yaml
# 语言设置 (zh/en)
language: zh

# 自动重载配置
auto-reload:
  delays: [30, 60] # 单位：秒
```

## 兼容性表格

| 服务端 | 兼容状态 | 说明 |
|------|------|------|
| Paper | ✅ | 已完成 |
| Spigot | ✅ | 已完成 |
|Mohist| ✅ | 已完成 |
|其他服务端| ❓ | 兼容性未知 |

## 注意❗
>当前模组仍有一点缺陷，多世界插件因为比本插件加载的速度**慢ProMax**，导致读取数据时无法找到多世界的世界数据，所有我们添加了自动重载，可以在config.yml来设为多世界完全加载后的时间，比如完全加载需要30秒就设为30，为了防止第一次加载失败，我们设定了两次，比如 [30,60] 就是30秒后重载一次和60秒后重载一次！
