using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

var builder = WebApplication.CreateBuilder(args);
{
    builder.Services.AddDbContext<TePrestoDb>(opt => opt.UseInMemoryDatabase("TodoList"));
    builder.Services.AddDatabaseDeveloperPageExceptionFilter();
}

var app = builder.Build();

//EndPoints
app.MapGet("/", () => "Hello World!");

app.MapGet("/ocupaciones", async (TePrestoDb db) =>
    await db.Ocupaciones.ToListAsync()
);

app.MapPost("/ocupaciones", async (Ocupaciones ocupacion, TePrestoDb db) =>
{
    db.Add(ocupacion);
    await db.SaveChangesAsync();

    return Results.Created($"/ocupaciones/{ocupacion.OcupacionId}", ocupacion);
});

app.Run();

public class Ocupaciones
{
    [Key]
    public int OcupacionId { get; set; }
    public string? Descripcion { get; set; }
}

public class TePrestoDb : DbContext
{
    public TePrestoDb(DbContextOptions<TePrestoDb> options)
        : base(options) { }

    public DbSet<Ocupaciones> Ocupaciones => Set<Ocupaciones>();
}