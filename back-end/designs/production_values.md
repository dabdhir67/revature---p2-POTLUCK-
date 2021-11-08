### Models
@Table(name = "chef", schema = "potluck")
@Table(name = "recipe", schema = "potluck")

### Logger
<Property name="logdir">${sys:catalina.home}/tmp/potluck_logs</Property>
